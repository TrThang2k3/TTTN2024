		function showAccount() {
			document.getElementById("accountContent").classList
					.remove("hidden");
			document.getElementById("ticketContent").classList.add("hidden");
		}

		function showTicket() {
			document.getElementById("accountContent").classList.add("hidden");
			document.getElementById("ticketContent").classList.remove("hidden");
		}

		function showPublisher() {
			document.getElementById("accountContent").classList.add("hidden");
			document.getElementById("ticketContent").classList.add("hidden");
			document.getElementById("publisherContent").classList
					.remove("hidden");
		}

		function showInvoice() {
			document.getElementById("accountContent").classList.add("hidden");
			document.getElementById("ticketContent").classList.add("hidden");
			document.getElementById("publisherContent").classList.add("hidden");
			document.getElementById("invoiceContent").classList
					.remove("hidden");
		}
		
/////////////////////////////////////////////////////////////////////////////////////////////		



