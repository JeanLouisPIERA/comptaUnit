pipeline {

		agent {
	        label 'master'
	    }
	
		triggers {
        	pollSCM('0-59/1 * * * *')
    	}
    	
		stages {
		
				stage ('Build'){
					steps{
					bat 'mvn clean compile'
	                }
				}
				
				stage ('Test'){
					steps{
					bat 'mvn verify'
					}
					post {
	                always {
	                    junit '**/target/surefire-reports/*.xml'
	                    step( [ $class: 'JacocoPublisher' ,
	                      changeBuildStatus : true,
	                      minimumInstructionCoverage : 0.7,
	                      minimumBranchCoverage : 0.7,
	                      minimumClassCoverage: 0.7,
	                      minimumComplexityCoverage: 0.7,
						  minimumLineCoverage: 0.7,
						  minimumMethodCoverage: 0.7
	                     ])
	                    }
					}
				}
				
				
        
		}
		
        
        
 } 