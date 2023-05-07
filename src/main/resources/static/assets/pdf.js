function generatePdf(id) {
    var pdf = new jsPDF('p', 'pt', 'letter');
var canvas = pdf.canvas;
var width = 600;
const init = document.querySelector("#ct"+id).style; 
document.querySelector("#ct"+id).style.position = "absolute";
document.querySelector("#ct"+id).style.top = "20px";
document.querySelector("#ct"+id).style.left = "20px";

//canvas.width=8.5*72;
document.body.style.width=width + "px";
html2canvas(document.querySelector("#content"+id), {
canvas:canvas,
onrendered: function(canvas) {
    var iframe = document.createElement('iframe');
    iframe.setAttribute('style', 'position:absolute;top:0;right:0;height:100%; width:600px');
    document.body.appendChild(iframe);
    iframe.src = pdf.output('datauristring');

   //var div = document.createElement('pre');
   //div.innerText=pdf.output();
   //document.body.appendChild(div);
}
});
document.querySelector("#ct"+id).style = init;
}