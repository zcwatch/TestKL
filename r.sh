#!/bin/sh
S0=${BASH_SOURCE[0]}
f=$1
if [ "$f" == '' ]; then
	echo "Usage:$S0 <file>"
    { [ "${0##*bash}" == "" ] && return; exit 1; }
fi
java -jar ${@:1}
