$(document).ready(function() {
  var input = $('input[name=stars]');

  function addStars(rating) {
    input.val(rating);
  }

  var stars = $("div#stars").rateYo({
    rating: 0,
    fullStar: true,
    starWidth: '20px',
    onChange: addStars,
    onSet: addStars
  });

});
