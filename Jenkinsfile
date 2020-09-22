pipeline {
agent any
stages {

stage ('Build'){
steps{
bat 'mvn -Dmaven.test.failure.ignore=true install'
}
}

stage ('Test'){
steps{
bat 'mvn -Dmaven.test.failure.ignore=true test'
}
}

}
}