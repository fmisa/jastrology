#!/bin/sh
JASTROLOGY_HOME=.
MAIN_CLASS=com.ivstars.astrology.gui.Chart
TMP_CP=$JASTROLOGY_HOME/target/build
for i in "$JASTROLOGY_HOME"/lib/*.jar; do
	TMP_CP="$TMP_CP":"$i"
done
java -Xmx256m  -cp $TMP_CP $MAIN_CLASS --jastrology-home $JASTROLOGY_HOME $*

