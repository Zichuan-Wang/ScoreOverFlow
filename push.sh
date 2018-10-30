#!/bin/sh



setup_git() {
  git config --global user.email "ci@azure.com"
  git config --global user.name "Azure CI"
}



commit_logs() {
  cat maven_log
  cat **/*PMD.html
  cat *PMD.html
  git checkout -b master
  git add maven_log
  git commit --message "Azure CI log"
}



upload_files() {
  #git remote add origin https://${GH_TOKEN}@github.com/HengruiX/ScoreOverFlow.git
  git push --quiet --set-upstream origin master
}



setup_git
commit_logs
upload_files
