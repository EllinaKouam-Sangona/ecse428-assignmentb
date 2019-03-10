Feature: Gmail
  
Scenario: Attaching file through the file system

	Given I am on the new email page
	When I select the attach files button
	And select the file to attach in my file explorer
	Then the file is included in my email
	And the file icon appears in my email 



Scenario: Attaching a file by dragging it 

	Given I am on the new email page
	When I drag a file from my computer into the email page, <image>
	Then the file is included in my email
	And the email is sent to <recipent> with my file
	
Scenario: Exceeding the maximum file size limit  
	
	Given I am on the new email page
	When I select the attach files button
	And select the file to attach in my file explorer, <image>
	Then a prompt saying Your file is larger than 25 MB appears
	And the email is sent to <recipient> with my file


Scenario: Not including an image 

	Given I am on the new email page
	When I select the attach files button
	And I click “open” on the file explorer
	Then the file explorer should prompt me to select a file



