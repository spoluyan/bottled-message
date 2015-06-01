function showTextInput() {
	$('#show-text-btn').addClass('hide');
	$('#text').removeClass('hide');
}

function hideTextInput() {
	$('#show-text-btn').removeClass('hide');
	$('#text').addClass('hide');
}

function showImgInput() {
	$('#show-img-btn').addClass('hide');
	$('#img').removeClass('hide');
}

function hideImgInput() {
	$('#show-img-btn').removeClass('hide');
	$('#img').addClass('hide');
}

function showVideoInput() {
	$('#show-video-btn').addClass('hide');
	$('#video').removeClass('hide');
}

function hideVideoInput() {
	$('#show-video-btn').removeClass('hide');
	$('#video').addClass('hide');
}

function preview() {
	var previewText = $('#preview-text');
	var previewImg = $('#preview-img');
	var previewVideo = $('#preview-video');
	
	previewText.text('');
	previewImg.html();
	previewVideo.html();
	
	if (isVisible($('#text'))) {
		previewText.text($('textarea').val());
	}
	
	if (isVisible($('#img')) && $('#img-link').val().trim() !== '') {
		previewImg.html('<img src="' + $('#img-link').val() + '">');
	}
	
	if (isVisible($('#video')) && $('#video-link').val().trim() !== '') {
		previewVideo.html('<div class="well well-lg">Video from ' + $('#video-link').val() + '</div>');
	}
	
	$('#preview-modal').modal('show');
}

function isVisible(obj) {
	return !(obj.hasClass('hide'));
}

function sendMessage() {
	
}