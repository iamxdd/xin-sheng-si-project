$(function () {
  $('.radioBtn').on('click', function () {
    $(this).addClass('active').attr('check', true).siblings().removeClass('active').attr('check', false)
  });
  $('.checkBtn').on('click', function () {
    if ($(this).attr('check') && $(this).attr('check') == 'true') {
      $(this).removeClass('active').attr('check', false)
    } else {
      $(this).addClass('active').attr('check', true)
    }
  })
})