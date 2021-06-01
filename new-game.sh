#!/bin/sh

if [ -z "$1" ]
    then
        echo 'Url of a new repository not specified!'
        exit 1
fi

echo 'Rename remote origin -> template'
git remote rename origin template

echo "Add new remote origin with url $1"
git remote add -f origin $1
