#!/bin/sh
JASTROLOGY_HOME=$INSTALL_PATH
if [ -z "$JASTROLOGY_HOME" ]; then
	JASTROLOGY_HOME=.
fi
MAIN_CLASS=com.ivstars.astrology.gui.Chart
if [ -d "$JASTROLOGY_HOME/classes" ]; then
	TMP_CP=$JASTROLOGY_HOME/classes
else
	TMP_CP=$JASTROLOGY_HOME/target/build
fi
for i in "$JASTROLOGY_HOME"/lib/*.jar; do
	TMP_CP="$TMP_CP":"$i"
done
java -Xmx256m  -cp $TMP_CP $MAIN_CLASS --jastrology-home $JASTROLOGY_HOME $@

