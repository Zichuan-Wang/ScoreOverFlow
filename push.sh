#!/bin/sh



setup_git() {
  git config --global user.email "ci@azure.com"
  git config --global user.name "Azure CI"
}



commit_logs() {
  cat maven_log
  cat **/*PMD.html
  cat *PMD.html
  find . -name *PMD* -print0 | xargs -0 -n1 dirname | sort --unique
  find . -name **/*PMD* -print0 | xargs -0 -n1 dirname | sort --unique
  git checkout -b master
  git add maven_log
  git commit -m "Azure CI log ***NO_CI***"
}



upload_files() {
  git remote set-url origin https://Zichuan-Wang:1e8af7f8ec67794a6e41d8b90123f15da0e361da@github.com/HengruiX/ScoreOverFlow.git
  git push --quiet --set-upstream origin master
}



setup_git
commit_logs
upload_files
