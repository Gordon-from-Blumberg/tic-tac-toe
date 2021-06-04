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

echo 'Set user name to gordon'
git config user.name gordon
echo 'Set user email to gordon.from.blumberg@gmail.com'
git config user.email gordon.from.blumberg@gmail.com
echo 'Set push.default to upstream'
git config push.default upstream

echo 'Rename branch master -> template'
git branch -m master template
echo 'Create a new branch master and checkout to it'
git checkout -b master

origin_master=$2
if [ -z "$origin_master" ]
    then
        echo 'Default branch name is not specified -> use master'
        origin_master=master
fi

echo "Set current branch master to remote origin/$origin_master"
git push --set-upstream origin $origin_master
