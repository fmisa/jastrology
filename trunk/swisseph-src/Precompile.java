import java.io.*;
import java.util.*;

/**
* A simple class to precompile Java source code similar to C precompilation.
* Instead of #define and #if / #ifdef etc., you would use //#define, //#if,
* //#ifdef etc.<p>
* The idea behind this is in having an option to generate similar sources
* out of one code base. So you can comment / uncomment different parts of
* the code via precompile switches in the source files or as arguments to
* this class (-D).<p>
* Syntax:<br>
* <blockquote>
* <code>
* java Precompile [-q] -o<OutputDir> [outputDirs ...] -i<InputDir> [inputDirs ...] {-D<definedParameter>...}<br>
* java Precompile -l -i<InputDir> [inputDirs ...]
* </code>
* </blockquote>
* In the first mode, you give this class two directories as parameter, one is the directory,
* where the input source files reside (-i), the other one is the
* directory, where Precompile.java should put the precompiled source files (-o). Be aware
* that any source files in the output directory will be overwritten without
* further notice! Optionally, you give any amount of <i>defines</i> with
* <code>-D<i>define-name</i> -D<i>define-name</i> ...</code> as additional
* parameters.<p>
* In the second mode, Precompile will list (-l or --list) all used define switches
* in all the source files.
* Supported precompile switches are:<br>
* <blockquote>
*  <ul>
*  <li><code>//#define &lt;value&gt;</code><br>
* <blockquote>
* Only to define a switch for evaluation by <code>//#ifdef</code> or
* <code>//#ifndef</code>. You can also #define values from the command line
* by the -D switch. Locally defined switches are restricted to the file in
* which they occur.
* </blockquote>
*  <li><code>//#undefine &lt;value&gt;</code><br>
* <blockquote>
* Only to undefine a previously set switch. //#undefine is also restricted to
* the file in which it appears.
* </blockquote>
*  <li><code>//#if &lt;value&gt; ... //#else ... //#endif</code><br>
* <blockquote>
* This can be <i>//#if 0</i> (==false) or <i>//#if 1</i> (or any
* other Integer != 0: ==true). Otherwise it is interpreted identical to
* <i>//#ifdef &lt;value&gt;</i>.
* </blockquote>
* <li><code>//#ifdef &lt;value&gt; ... //#else ... //#endif</code><br>
* <blockquote>
* True, if &lt;value&gt; is defined prior to this line of code, false otherwise.
* </blockquote>
* <li><code>//#ifndef &lt;value&gt; ... //#else ... //#endif</code><br>
* <blockquote>
* Equal to <code>//#ifdef</code>, but with reversed logic.
* </blockquote>
*  </ul>
* </blockquote>
*/
public class Precompile {
  String[] files=null;
  Vector vDefines=new Vector();
  Vector vGlDefines;             // A backup copy of the globally defined
                                 // switches.
  Vector vLevels=new Vector();
  Vector vWarnings=new Vector();
  String fname;                  // Current filename
  long lineCnt;
  Vector outDir=new Vector();
  Vector inDir=new Vector();
  boolean listDefines = false;
  boolean silent = false;

  /**
  * This is the routine to precompile all *.java files in a given directory.
  * It takes two directories as input parameter: in which directory do we
  * look for the source files, and into which directory to put the precompiled
  * sources.<br>
  * @param inputDirs In which directory are the java source files? ALL Java
  * source files in these directories will be precompiled.
  * @param outputDirs In which directories to put the precompiled source files.
  * It does not have to exist, it will be created automatically if needed.
  * <b><i>Attention:</b> Any existing files in this directory will be
  * overwritten without notice!</i>
  * @param switches Any &lt;DEFINES&gt; to be used as precompile options.
  * Can be null if none should be applied.
  * @return A String array with warnings. Each warning String consists of three
  * parts, first the filename, then the lineNumber and then the error message.
  * All three parts are separated by '\n'.
  */
  public String[] precompile(String[] inputDirs,
                             String[] outputDirs,
                             String[] switches) {
    int len=0;
    if (switches!=null) { len=switches.length; }
    int cnt=inputDirs.length+outputDirs.length;
    String[] sw=new String[len+cnt];
    int n=0;
    sw[0]="-i";
    for (int i=0; i<inputDirs.length; i++) {
      if (inputDirs[i].startsWith("-")) {
        sw[n]="./"+inputDirs[i];
      } else {
        sw[n]=inputDirs[i];
      }
      n++;
    }
    sw[1]="-o";
    for (int i=0; i<outputDirs.length; i++) {
      if (outputDirs[i].startsWith("-")) {
        sw[n+i]="./"+outputDirs[i];
      } else {
        sw[n+i]=outputDirs[i];
      }
      n++;
    }
    for(int i=0;i<len;i++) {
      sw[n+i]="-D"+switches[i];
    }
    readParams(sw);
    for(int dirs=0; dirs<inDir.size(); dirs++) {
      files=getFiles(dirs);
      for(int i=0; i<files.length; i++) {
        fname=files[i];
        lineCnt=0;
        precompileFile((String)inDir.elementAt(dirs),
                       (String)outDir.elementAt(dirs));
      }
    }
    String[] aWarnings=new String[vWarnings.size()];
    for(int i=0; i<vWarnings.size(); i++) {
      aWarnings[i]=(String)vWarnings.elementAt(i);
    }
    return aWarnings;
  }

  /**
  * You can run the class stand alone via this method. As parameters, you
  * have to specify two directories, optional are any number of switches
  * for precompilation.<p>
  * Parameters in detail:
  * <blockquote>
  * <ul>
  * <li><code>-i &lt;directory&gt;</code> The directory containing the input
  * source files. All *.java files in this directory will be precompiled. You
  * can give multiple directories with <code>-idir1 dir2 dir3 ...</code>. If
  * a directory starts with '-', you will have to prefix this directory with
  * ./ oder .\\ on Windows.
  * <li><code>-o &lt;directory&gt;</code> The directory to where the precompiled
  * source files will be written. You can give multiple directories in the same
  * way as it is explained above for the -i switch.
  * <li><code>-D&lt;switch&gt;</code> (Optional) Any switch to be used for
  * precompilation. You can have any number of -D options.
  * </ul>
  * </blockquote>
  * @param params An array of Strings. See above for a more detailed
  * explanation of possible parameters.
  */
  public static void main(String[] params) {
    Precompile p=new Precompile();
    try {
      p.readParams(params);
    } catch (IllegalArgumentException ia) {
      System.err.println(ia.getMessage());
      System.exit(1);
    }
    if (p.listDefines) {
      p.listDefineSwitches(p.inDir);
    } else {
      for(int dirs=0; dirs<p.inDir.size(); dirs++) {
        p.files=p.getFiles(dirs);
        for(int i=0; i<p.files.length; i++) {
          p.fname=p.files[i];
          p.lineCnt=0;
          p.precompileFile((String)p.inDir.elementAt(dirs),
                           (String)p.outDir.elementAt(dirs));
          for(int j=0; j<p.vWarnings.size(); j++) {
            p.printWarning((String)p.vWarnings.elementAt(j));
          }
          p.vWarnings.removeAllElements();
        }
      }
    }
  }

  // Only for the main() method...
  void printWarning(String w) {
    int idx1=w.indexOf("\n");
    int idx2=w.indexOf("\n",idx1+1);
    String file=w.substring(0,idx1);
    String line=w.substring(idx1+1,idx2);
    System.err.println(file+" ("+line+"): "+w.substring(idx2+1));
  }




  void readParams(String[] p) {
    boolean err=false;
    int i=0;
    for(; i<p.length; i++) {
      if (p[i].startsWith("-D")) {
        String def;
        if (p[i].length()>2) { def=p[i].substring(2);
        } else if (i+1<p.length) { def=p[++i];
        } else { err=true; break;
        }
        vDefines.addElement(def);
      } else if (p[i].startsWith("-o") && outDir.size() == 0) {
        if (p[i].length()>2) { outDir.addElement(p[i].substring(2));
        } else if (i+1<p.length) { outDir.addElement(p[++i]);
        } else { err=true; break;
        }
        while(i+1<p.length && !p[i+1].startsWith("-")) {
          outDir.addElement(p[++i]);
        }
      } else if (p[i].startsWith("-i") && inDir.size() == 0) {
        if (p[i].length()>2) { inDir.addElement(p[i].substring(2));
        } else if (i+1<p.length) { inDir.addElement(p[++i]);
        } else { err=true; break;
        }
        while(i+1<p.length && !p[i+1].startsWith("-")) {
          inDir.addElement(p[++i]);
        }
      } else if (p[i].equals("-q")) {
        silent = true;
      } else if (p[i].equals("-l") || p[i].equals("--list")) {
        listDefines = true;
      } else {
        err=true; break;
      }
    }
    if (err) {
      throw new IllegalArgumentException(
                         "Unknown parameter or missing value for "+
                         "parameter '"+p[i]+"'.\nSyntax:\n"+
                         "java Precompile [-q] -o<OutputDir> [OutputDirs ...] -i<InputDir> [InputDirs...] "+
                         "{-D<definedParameter>...}\n"+
                         "java Precompile -l -i<InputDir> [InputDirs...]");
    }
    if (listDefines && inDir.size() == 0) {
      throw new IllegalArgumentException(
                         "Missing parameter -i.\nSyntax:\n"+
                         "java Precompile [-q] -o<OutputDir> [OutputDirs ...] -i<InputDir> [InputDirs...] "+
                         "{-D<definedParameter>...}\n"+
                         "java Precompile -l -i<InputDir> [InputDirs...]");
    }
    if (listDefines && outDir.size() != 0) {
      System.err.println("Ignoring parameter -o in list mode.\n" +
                         "Syntax:\n"+
                         "java Precompile [-q] -o<OutputDir> [OutputDirs ...] -i<InputDir> [InputDirs...] "+
                         "{-D<definedParameter>...}\n"+
                         "java Precompile -l -i<InputDir> [InputDirs...]");
    }
    if (listDefines && vDefines.size() != 0) {
      System.err.println("Ignoring parameter" + (vDefines.size()==1?"":"s") +
                         " -D... in list mode.\nSyntax:\n"+
                         "java Precompile [-q] -o<OutputDir> [OutputDirs ...] -i<InputDir> [InputDirs...] "+
                         "{-D<definedParameter>...}\n"+
                         "java Precompile -l -i<InputDir> [InputDirs...]");
    }
    if (!listDefines && (inDir.size() == 0 || outDir.size() == 0)) {
      throw new IllegalArgumentException(
                         "Missing parameters -i and / or -o.\nSyntax:\n"+
                         "java Precompile [-q] -o<OutputDir> [OutputDirs ...] -i<InputDir> [InputDirs...] "+
                         "{-D<definedParameter>...}\n"+
                         "java Precompile -l -i<InputDir> [InputDirs...]");
    }
    if (!listDefines && inDir.size() != outDir.size()) {
      throw new IllegalArgumentException(
                         "Number of input directories ("+inDir.size()+") and "+
                         "number of output directories ("+outDir.size()+
                         ") have to match!");
    }
    vGlDefines=(Vector)vDefines.clone();
  }

  String[] getFiles(int index) {
    String in = (String)(inDir.elementAt(index));
    String out = (String)(outDir.elementAt(index));
    if (inDir.equals(outDir)) {
      throw new IllegalArgumentException(
                         "Input directory and output directory have to "+
                         "be different ('"+inDir+"')!");
    }

    File fInDir=new File(in);
    if (!fInDir.isDirectory()) {
      throw new IllegalArgumentException("Directory '"+inDir+"' not found.");
    }
    if (!listDefines) {
      File fOutDir=new File(out);
      if (fOutDir.exists() && !fOutDir.isDirectory()) {
        throw new IllegalArgumentException(
                                     "Cannot create directory '"+outDir+"'.");
      } else if (!fOutDir.exists() && !fOutDir.mkdirs()) {
        throw new IllegalArgumentException(
                                     "Cannot create directory '"+outDir+"'.");
      }
    }

    files=fInDir.list(new JavaFilter());
    if (files.length<1) {
      throw new IllegalArgumentException(
                         "No source files (\""+inDir+File.separator+
                         "*.java\") found.");
    }
    // Clean up output directory
    for(int k=0; k<files.length; k++) {
//System.out.println("Deleting "+outDir+File.separator+files[k]);
      new File(outDir+File.separator+files[k]).delete();
    }

    return files;
  }

Vector vDefNames = new Vector();

  void listDefineSwitches(Vector inDir) {
    BufferedReader fIn=null;
    for(int dirs=0; dirs<inDir.size(); dirs++) {
      String in = (String)(inDir.elementAt(dirs));
      File fInDir=new File(in);
      if (!fInDir.isDirectory()) {
        throw new IllegalArgumentException("Directory '"+inDir+"' not found.");
      }
      files=fInDir.list(new JavaFilter());
      if (files.length<1) {
        throw new IllegalArgumentException(
                       "No source files (\""+inDir+File.separator+
                       "*.java\") found.");
      }
      for(int i=0; i<files.length; i++) {
        try {
          fIn=new BufferedReader(new FileReader(in+File.separator+files[i]));
          boolean found;
          String line;
          while((line=fIn.readLine())!=null) {
            if (line.startsWith("//#if")) {
              int idx = line.indexOf(" ");
              String define = line.substring(idx).trim();
              idx = define.indexOf(" ");
              if (idx > 0) {
                define = define.substring(0,idx).trim();
              }
              found = false;
              for (int j = 0; j < vDefNames.size(); j++) {
                if (((String)vDefNames.elementAt(j)).equals(define)) {
                  found = true;
                  break;
                }
              }
              if (!found && !"0".equals(define) && !"1".equals(define)) {
                vDefNames.addElement(define);
              }
            }
          }
          fIn.close();
        } catch (IOException io) {
          try { fIn.close(); } catch (Exception e2) { }
          vWarnings.addElement(fname+"\n"+lineCnt+"\nIOException: "+io);
        }
      }
    }
    for (int i = 0; i < vDefNames.size(); i++) {
      System.out.println(vDefNames.elementAt(i));
    }
  }

  void precompileFile(String indirname, String outdirname) {
    if (!silent) {
      System.out.println("Processing "+indirname+File.separator+fname);
    }
    BufferedReader fIn=null;
    BufferedWriter fOut=null;

    // Initialize the #defines to the state of calling the precompile routine:
    vDefines=(Vector)vGlDefines.clone();

    try {
      fIn=new BufferedReader(new FileReader(indirname+File.separator+fname));
      fOut=new BufferedWriter(new FileWriter(outdirname+File.separator+fname));
      String line="";
      StringTokenizer tk=null;
      Boolean write=new Boolean(true);
      int max=0;
      int lastmax=0;
      boolean update=false;

      while((line=fIn.readLine())!=null) {
        lineCnt++;
        lastmax=max;
        max=vLevels.size()-1;
        if ((max>=0 && lastmax!=max) || update) {
          update=false;
          write=new Boolean(true);
          for (int i=0; i<=max; i++) {
            if (vLevels.elementAt(i).toString().equals("false")) {
              write=new Boolean(false);
              break;
            }
          }
        } else if (lastmax!=max) {
          write=new Boolean(true);
        }
        if (line.startsWith("//#")) {
          tk=new StringTokenizer(line);
          String cmd=tk.nextToken();
          if (cmd.startsWith("//#if")) {
            update=true;
            boolean b=evaluate(cmd, tk, write.booleanValue());
            write=new Boolean(b);
            vLevels.addElement(write);
          } else if (cmd.equals("//#endif")) {
            if (max<0) {
              vWarnings.addElement(fname+"\n"+lineCnt+"\n//#endif found "+
                                   "without previous //#if or //#ifdef or "+
                                   "//#ifndef.");
            } else {
              vLevels.removeElementAt(max);
            }
          } else if (cmd.equals("//#else")) {
            if (max<0) {
              vWarnings.addElement(fname+"\n"+lineCnt+"\n//#else found "+
                                   "without previous //#if or //#ifdef or "+
                                   "//#ifndef.");
            } else {
              write=new Boolean(!(write.booleanValue()));
              vLevels.setElementAt(write, max);
              update=true;
            }
          } else if (cmd.equals("//#define") && write.booleanValue()) {
            try {
              vDefines.addElement(tk.nextToken());
            } catch (NoSuchElementException nex) {
              vWarnings.addElement(fname+"\n"+lineCnt+"\n//#define found "+
                                   "without any value.");
            }
          } else if (cmd.equals("//#undefine") && write.booleanValue()) {
            try {
              String val=tk.nextToken();
              if (!vDefines.removeElement(val)) {
                vWarnings.addElement(fname+"\n"+lineCnt+"\n//#undefine "+val+
                                     " found without "+val+" being previously "+
                                     "defined.");
              }
            } catch (NoSuchElementException nex) {
              vWarnings.addElement(fname+"\n"+lineCnt+"\n//#undefine found "+
                                   "without any value.");
            }
          } else if (write.booleanValue()) {
            vWarnings.addElement(fname+"\n"+lineCnt+"\nUnknown directive '"+
                                 cmd+"' found.");
          }
        } else if (write.booleanValue()) {
          fOut.write(line+"\n");
        }
      }
      fIn.close();
      fOut.close();
    } catch (IOException i) {
      try { fIn.close(); } catch (Exception e2) { }
      try { fOut.close(); } catch (Exception e3) { }
      vWarnings.addElement(fname+"\n"+lineCnt+"\nIOException: "+i);
    }
    int miss=vLevels.size();
    if (miss>0) {
      vWarnings.addElement(fname+"\n"+lineCnt+"\nMissing "+miss+" '//#endif' "+
                           "statement"+(miss>1?"s":"")+" on end of file!");
      vLevels.removeAllElements();
    }
  }

  boolean evaluate(String cmd, StringTokenizer tk, boolean w) {
    String name;
    if (cmd.equals("//#if")) {
      if (!w) {
        return false;
      } else if (!tk.hasMoreTokens()) {
        vWarnings.addElement(fname+"\n"+lineCnt+"\n//#if found without any "+
                             "value.");
        return false;
      }
      String val=tk.nextToken();
      try {
        return (Integer.parseInt(val)!=0);
      } catch (NumberFormatException nfe) {
        name=val;
      }
    } else {
      name=tk.nextToken();
    }
    boolean found=false;
    for(int i=0; i<vDefines.size(); i++) {
      found=found || ((String)vDefines.elementAt(i)).equals(name);
    }
    if (cmd.equals("//#ifdef") ||
        cmd.equals("//#if")) { // The logic for #if is not identical to the
                               // logic of #ifdef in C, but here and in this
                               // case, it is...
       return found;
    } else if (cmd.equals("//#ifndef")) {
       return !found;
    } else {
       return false;
    }
  }
}



class JavaFilter implements FilenameFilter {
  public boolean accept(File dir, String name) {
     return name.endsWith(".java");
  }
}
