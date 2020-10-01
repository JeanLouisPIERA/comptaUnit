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
	            bat "mvn clean compile "
	            }
	        }
	        stage("Compile the test source code")	{
	            steps	{
	            bat "mvn test-compile  "
	            
	            
	            }
	            
	       }
	       
	       stage("Run tests Junit dans le package business")	{
	            steps	{
	            bat "mvn test -P unittests"
	            
	            
	            }     
	            
	        }
	        
	        
	        stage("Run tests Junit dans tous les packages  ")	{
	            steps	{
	            bat "mvn package -P inttests"
	            
	            
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
				
				 publishHTML	(target:	[
							allowMissing: false,
						    alwaysLinkToLastBuild: true,
					        keepAll: true,
					        reportDir: 'myerp-technical/target/site/jacoco',
							reportFiles:	'index.html',
							reportName:	"myerp-technical coverage report"
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