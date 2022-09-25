import { getApiUrl } from './utils/url';

const appContainer = document.querySelector('#appContainer'),
	apiUrl = 'http://localhost:8080/api/v1';

function recupApiwithdesc(req) {
	fetch(`${apiUrl}/interface/espece?desc=${req}`)
		.then(response => response.text())
		.then(message => (appContainer.innerHTML = message));
}

function recupApiwithid(req) {
	fetch(`${apiUrl}/interface/espece?id=${req}`)
		.then(response => response.text())
		.then(message => (appContainer.innerHTML = message));
}

const inputSearch = document.querySelector(`#myInput`);
inputSearch.addEventListener(`keypress`, a => {
	a.preventDefault;
	if (a.code == 'Enter') {
		recupApiwithdesc(a.target.value);
	}
});
const inputButton = document.querySelector(`#myButton`);
inputButton.addEventListener('click', press => {
	press.preventDefault;
	if (inputSearch.value != '') recupApiwithdesc(inputSearch.value);
});

var toggler = document.getElementsByClassName('caret');
var i;

for (i = 0; i < toggler.length; i++) {
	toggler[i].addEventListener('click', function () {
		this.parentElement.querySelector('.nested').classList.toggle('active');
		this.classList.toggle('caret-down');
	});
}
