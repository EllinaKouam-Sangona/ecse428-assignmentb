Feature: Gmail
  
Scenario Outline: Attaching file through the file system

	Given I am on the new email page
	When I enter <recipient> and the email body
	And I select the attach files button
	And select the file to attach in my file explorer, <image>
	Then the file <image> is included in my email 
	And the email is sent

Examples:
| recipient                | image |
| chaimaefahmi@outlook.com | Image.png |
| chaimaef@outlook.com     | red.png |
| chaimae.fahmi@mail.mcgill.ca | red.png|
| chaimaefahmi@outlook.com | red.png |
| chaimaef@outlook.com     | Image.png |

Scenario Outline: Attaching a file by dragging it 

	Given I am on the new email page
	When I drag a file from my computer into the email page
	Then the file is included in my email
	And the file icon appears in my email
	
Examples:
| recipient                | image |
| chaimaefahmi@outlook.com | Image.png |
| chaimaef@outlook.com     | red.png |

Scenario Outline: Exceeding the maximum file size limit  
	
	Given I am on the new email page
	When I select the attach files button
	And select the file to attach in my file explorer
	And the file exceeds  25 MB
	Then a prompt saying �Your file is larger than 25 MB. It will be sent as a Google Drive link.
	And the file should be included as a google drive file

Examples:
| recipient                | image |
| chaimaefahmi@outlook.com | Image.png |
| chaimaef@outlook.com     | red.png |
#
Scenario Outline: Not including an image 

	Given I am on the new email page
	When I enter <recipient> and the email body
	And I select the attach files button
	And I click open on the file explorer
	Then the open button should be non interactable

Examples:
| recipient     |           
| chaimaefahmi@outlook.com |
| chaimaef@outlook.com  |  
| chaimae.fahmi@mail.mcgill.ca |
