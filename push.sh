#!/bin/sh

setup_git() {
  git config --global user.email "ci@travis-ci.org"
  git config --global user.name "Travis CI"
}

commit_logs() {
  git checkout master
  mkdir -p reports/maven
  mkdir -p reports/pmd
  mkdir -p reports/test
  mv ./target/surefire-reports/*.txt reports/test
  mv ./target/cpd.xml reports/pmd
  mv ./target/pmd.xml reports/pmd
  ll -r ./target
  mv ./target/site/jacoco/* reports/jacoco
  mv maven_phase1.log reports/maven
  mv maven_phase2.log reports/maven
  git add reports/pmd/cpd.xml
  git add reports/pmd/pmd.xml
  git add reports/test/*.txt
  git add reports/jacoco/*
  git add reports/maven/maven_phase1.log
  git add reports/maven/maven_phase2.log
  git commit -m "Travis CI log [skip ci]"
}

upload_files() {
  git remote set-url origin https://Zichuan-Wang:$AccessKey@github.com/Zichuan-Wang/ScoreOverFlow.git
  git push --quiet --set-upstream origin master
}



setup_git
commit_logs
upload_files
