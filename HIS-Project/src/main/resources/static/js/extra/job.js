$(document).ready(function(){
	$("#rDetails").hide();
	$(function() {
		  $("form[id='jobform']").validate({
		    rules: {

		    	occupation: "required",
		    	orgName: "required",
		    	jobPosition: "required",
		    	startJob : {
		    		required : true,
		    		date : true,
		    	},
		  		income : {
			    	required : true,
			    	number : true,
			    	maxlength : 10,
			    	minlength : 4
			    },
			    incomeTFS : {
			    	required : true,
			    	number : true,
			    	maxlength : 10,
			    	minlength : 2
			    },

		    },

		    startJob : {
		    	date : "Pleace enter the started date",
		    	required : "Pleace enter started date",
		    	
		    },
	  		income : {
		    	required : "Pleace enter income",
		    	number : "you must enter only digit",
		    	maxlength : "maximum length 10 digits",
		    	minlength : "minimum length 4 digits"
		    },
		    incomeTFS : {
		    	required : "Pleace enter Tax",
		    	number : "you must enter only digit",
		    	maxlength : "maximum length 10 digits",
		    	minlength : "minimum length 2 digits"
		    },
		    messages: {
		    	occupation: "Please enter  occupation",
		    	orgName: "Pleace enter orginization name",
		    	jobPosition: "Please enter job position",
		    },

		    submitHandler: function(form) {
		      form.submit();
		    }
		  });
		});
});