//import https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.6.1/angular.min.js
//import https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js

function createNode(element) {
    return document.createElement(element);
  }
function append(parent, el) {
    return parent.appendChild(el); 
  }

const ign_ul = document.getElementById('results');
//const url = 'https://randomuser.me/api/?results=10'
const ign_url_articles = 'https://ign-apis.herokuapp.com/articles?startIndex=30\u0026count=5'; 
const ign_url_videos = 'https://ign-apis.herokuapp.com/videos?startIndex=30\u0026count=5';

$(document).ready(function(){
  var art = document.getElementById('article')
  var vid = document.getElementById('video')
  $(vid).addClass("selected");
  displayResults(ign_url_videos, "video");
  $("#video").click(function(){
    $(art).removeClass("selected");
    $(vid).addClass("selected");
    displayResults(ign_url_videos, "video");
  });
  $("#article").click(function(){
    $(vid).removeClass("selected");
    $(art).addClass("selected");
    displayResults(ign_url_articles, "article");
  });
});

$(document).on('click', 'li', function(){
    console.log("list item clicked");
    $("a", this).toggle();
    $("img", this).toggle();
});

$(document).on('mouseover', 'a', function(){
    console.log("a hovered");
    //$("img", this).toggle();
});

function logResults(json, type){

  while (ign_ul.hasChildNodes()) {
    ign_ul.removeChild(ign_ul.lastChild);
}
  
  let results = json.data;
  return results.map(function(result) {
      let li = createNode('li'),
          img = createNode('img'),
          span2 = createNode('span'),
          div = createNode('div'),
          div2 = createNode('div'),
          a = createNode('a'),
          span3 = createNode('span'),
          span4 = createNode('span'),
          span = createNode('span');   
      img.src = result.thumbnails[0].url;
    a.href = result.metadata.url;
    span4.innerHTML = "GO TO IGN";
   
    if(type == "article"){
      span.innerHTML = `${result.metadata.headline}`;
      span2.innerHTML = ` ${result.metadata.subHeadline}`;
    }
    if(type == "video"){
      span.innerHTML = `${result.metadata.name}`;
      span2.innerHTML = ` ${result.metadata.description}`;
      var duration = ` ${result.metadata.duration}`;
      span3.innerHTML = convertToMMSS(duration);
      //a.innerHTML = `WATCH`;
    }
      //append(li, img);
      //append(span, span2);
      //append(span3, a);
      append(div, span3);
      append(div, span);
      append(div, span2);
      //append(li, span);
      append(li, div);
      
      //append(img, span4);
      append(a, span4);
      append(a, img);
      append(li, a);
      //append(li, div);
      //append(div, li);
      append(ign_ul, li);
    })
  }
  //.catch(function(error) {
    //console.log(error);
  //});  

function convertToMMSS(seconds){
  
  var s = seconds % 60;
  var m = Math.floor((seconds % 3600 ) / 60);
  
  return m + ":" + s;
}

function displayResults(url, resultType){
  $.ajax({
  url: url,
  dataType: "jsonp",
  jsonpCallback: "logResults",
  success: function(jData) {
              self.logResults(jData, resultType);
           }
});
}
