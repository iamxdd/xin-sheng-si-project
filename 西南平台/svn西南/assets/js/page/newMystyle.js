$(function () {
  $('.radioBtn').on('click', function () {
    $(this).addClass('active').attr('check', true).siblings().removeClass('active').attr('check', false)
  });
  
 
  	$('.gaseous .checkBtn').on('click', function () {
	 if($("input[name='forecastMode1").is(":checked")){
		if ($(this).attr('check') && $(this).attr('check') == 'true') {
		  $(this).removeClass('active').attr('check', false)
		} else {
		  $(this).addClass('active').attr('check', true)
		}
	}
 	 })
 
  

  	$('.pm25 .checkBtn').on('click', function () {
  	if($("input[name='forecastMode2").is(":checked")){
    	if ($(this).attr('check') && $(this).attr('check') == 'true') {
     		 $(this).removeClass('active').attr('check', false)
    	} else {
     		 $(this).addClass('active').attr('check', true)
   		} 
  	 }
 	 })
 
  /*$('.checkBtn').on('click', function () {
    if ($(this).attr('check') && $(this).attr('check') == 'true') {
      $(this).removeClass('active').attr('check', false)
    } else {
      $(this).addClass('active').attr('check', true)
    }
  })*/

  let getStationUrl = window.pageConfig.ajaxUrl.getStation
  $.get(getStationUrl, function (success) {
    let stationData = success.Result
    let provinceArr = stationData.map(item => {
      return `<option value="${item.provinceName}">${item.provinceName}</option>`
    })
    $('select[name="province"]').html(provinceArr.join(''))

    let proIndex = 0
    let cityIndex = 0

    proIndex = $('select[name="province"]').prop('selectedIndex')
    citySelect()

    $('select[name="province"]').on('change', function () {
      proIndex = $(this).prop('selectedIndex')
      citySelect()
    })

    $('select[name="city"]').on('change', function () {
      cityIndex = $(this).prop('selectedIndex')
      StationSelect()
    })

    function citySelect () {
      let cityArr = stationData[proIndex].provinceCities.map(item => {
        return `<option value="${item.cityName}" data-id="${item.cityCode}">${item.cityName}</option>`
      })
      $('select[name="city"]').html(cityArr.join(''))
      
      cityIndex = $('select[name="city"]').prop('selectedIndex')
      StationSelect()
    }

    function StationSelect () {
      let cityArr = stationData[proIndex].provinceCities[cityIndex].stations.map(item => {
        return `<option value="${item.stationName}">${item.stationName}</option>`
      })
      cityArr.unshift(`<option value="均值">均值</option>`)
      $('select[name="station"]').html(cityArr.join(''))
    }
    
  })
})