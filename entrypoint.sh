#!/usr/bin/env bash

start(){
    echo "--> INFO: starting lein ring server"
    lein ring server-headless
}

case $1 in

run)
    shift 1
    start $@
;;

*)
   >&2 echo "---> INFO: running: '$1'."
;;
esac

