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
  git commit --message "Azure CI log ***NO_CI***"
}



upload_files() {
  git remote set-url origin https://Zichuan-Wang:9cade35bf393291c9ac26f3397fdf232437c7d14@github.com/HengruiX/ScoreOverFlow.git
  git push --quiet --set-upstream origin master
}



setup_git
commit_logs
upload_files
