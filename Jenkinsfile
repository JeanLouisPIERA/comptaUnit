pipeline {
agent any
stages {

stage ('Build'){
step{
sh 'mvn -Dmaven.test.failure.ignore=true install'
}
}

stage ('Test'){
step{
sh 'mvn -Dmaven.test.failure.ignore=true test'
}
}

}
}