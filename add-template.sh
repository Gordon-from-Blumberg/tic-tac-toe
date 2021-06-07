#!/bin/sh

echo 'Set user name to gordon'
git config user.name gordon
echo 'Set user email to gordon.from.blumberg@gmail.com'
git config user.email gordon.from.blumberg@gmail.com
echo 'Set push.default to upstream'
git config push.default upstream

echo 'Add template origin'
git remote add -f template git@github.com:Gordon-from-Blumberg/game_template.git