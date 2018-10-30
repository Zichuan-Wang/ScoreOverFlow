#!/bin/sh



setup_git() {
  git config --global user.email "ci@azure.com"
  git config --global user.name "Azure CI"
}



commit_logs() {
  git checkout -b master
  git add **/TEST-*.xml
  git commit --message "Azure CI log"
}



upload_files() {
  git remote add origin https://${GH_TOKEN}@github.com/HengruiX/ScoreOverFlow.git
  git push --quiet --set-upstream origin master
}



setup_git
commit_logs
upload_files
