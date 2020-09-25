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
	        stage("Test the source code")	{
	            steps	{
	            bat "mvn test"
	            }
	        }
	         stage("Code coverage. Limiting the minimum score for lines coverage to 75%")	{
	            steps	{
	            bat "mvn test jacoco:report"
	            publishHTML	(target:	[
					reportDir:	'src/myerp-business/target/site/jacoco-ut',
					reportFiles:	'src/myerp-business/target/site/jacoco-ut/index.html',
					reportName:	"Code coverage report"
				])
	            bat "mvn clean verify"
	            
	            }
	        }
			stage("Package the application")	{
		            steps	{
		            bat "mvn clean package -DskipTests"
		            }
		        }
	
	    }
	        
	        
	 } 