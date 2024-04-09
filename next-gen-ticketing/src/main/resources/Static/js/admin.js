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

let slideIndex = 0;

function moveSlide(n) {
	const slides = document.querySelector('.slides');
	const slideWidth = document.querySelector('.card').offsetWidth;
	slideIndex += n;
	if (slideIndex >= slides.children.length) {
		slideIndex = 0;
	}
	if (slideIndex < 0) {
		slideIndex = slides.children.length - 1;
	}
	const moveDistance = -slideWidth * slideIndex;
	slides.style.transform = `translateX(${moveDistance}px)`;
}

/////////////////////////////////////////////////////////////////////////////////////////////		



