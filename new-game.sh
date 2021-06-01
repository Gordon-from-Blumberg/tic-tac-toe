#!/bin/sh

echo 'Rename remote origin -> template'
git remote rename origin template

echo "Add new remote origin with url $1"
git remote add -f origin $1
