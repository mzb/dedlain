function formalLink(link) {
	var confirmed = link.getAttribute('data-confirm') ? 
			confirm(link.getAttribute('data-confirm')) : true;
	if (confirmed) {
		var form = document.createElement('form');
		form.style.display = 'none';
		link.parentNode.appendChild(form);
		form.setAttribute('method', link.getAttribute('data-method') || 'post');
		form.setAttribute('action', link.getAttribute('href'));
		form.submit();	
	}
	return false;
}