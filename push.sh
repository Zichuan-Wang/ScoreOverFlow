#!/bin/sh

setup_git() {
  git config --global user.email "ci@travis-ci.org"
  git config --global user.name "Travis CI"
}

commit_logs() {
  mvn pmd:pmd pmd:cpd
  git checkout master
  git add -f ./target/maven-pmd-plugin-default.xml
  git add -f ./target/cpd.xml
  git add -f ./target/pmd.xml
  git add -f ./target/surefire-reports
  git commit -m "Travis CI log [skip ci]"
}

upload_files() {
  git remote set-url origin https://Zichuan-Wang:$AccessKey@github.com/Zichuan-Wang/ScoreOverFlow.git
  git push --quiet --set-upstream origin master
}



setup_git
commit_logs
upload_files
