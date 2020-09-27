pipeline {

		agent {
	        label 'master'
	    }
	
		triggers {
        	pollSCM('0-59/1 * * * *')
    	}
    	
		stages{
		
	        stage("Compile the source code")	{
	            steps	{
	            bat "mvn compile"
	            }
	        }
	        stage("Compile the test source code")	{
	            steps	{
	            bat "mvn test-compile  "
	            
	            
	            }
	            
	       }
	        stage("Run tests using the suitable unit testing framework Junit")	{
	            steps	{
	            bat "mvn test  "
	            
	            
	            }     
	            
	        }
	        
	        stage("Processing the package where to run integration tests")	{
	            steps	{
	            bat "mvn integration-test  "
	            
	            
	            }     
	            
	        }
	        
	         stage("Code coverage. Limiting the minimum score for lines coverage to 75%")	{
	            steps	{
	            bat " mvn jacoco:check jacoco:report"
	      		
	      		
	            publishHTML	(target:	[
							allowMissing: false,
						    alwaysLinkToLastBuild: true,
					        keepAll: true,
					        reportDir: 'myerp-business/target/site/jacoco',
							reportFiles:	'index.html',
							reportName:	"myerp-business coverage report"
							])
				
				
				
	            publishHTML	(target:	[
							allowMissing: false,
						    alwaysLinkToLastBuild: true,
					        keepAll: true,
					        reportDir: 'myerp-model/target/site/jacoco',
							reportFiles:	'index.html',
							reportName:	"myerp-model coverage report"
							])
				
				
	            bat "mvn clean verify"
	            }
	            post {
	                always {
	                    junit '**/target/surefire-reports/*.xml'
	                    step( [ 
						  $class: 'JacocoPublisher'
						])
	                    }
	            	}
	        }
			stage("Package the application")	{
		            steps	{
		            bat "mvn clean package -DskipTests"
		            }
		        }
	
	    }
	        
	        
	 } 