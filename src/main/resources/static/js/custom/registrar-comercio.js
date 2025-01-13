document.addEventListener("DOMContentLoaded",init,false);


function init(){
    let contacto = document.querySelector('.contacto');
    let inputs = contacto.querySelectorAll('input');
    for (const entrada of inputs){
        entrada.addEventListener('blur', validar);
    }
}


function validar(evento){
    let contenedor = evento.target.parentElement;
    let labels = contenedor.querySelectorAll('label');  // Selecciona todos los labels en el contenedor
    let inputs = contenedor.querySelectorAll('input');  // Selecciona todos los inputs en el contenedor

    let inputsPrimerosTres = [...inputs].slice(0, 3);
    let labelsPrimerosTres = [...labels].slice(0, 3);

    if (inputsPrimerosTres.some(input => input.value.trim())) {
        labelsPrimerosTres.forEach(label => {
            label.textContent = '*' + label.textContent.replace(/^\*/, '');
        });
        inputsPrimerosTres.forEach(input => {
            input.required = true;
        });
    } else {
        labelsPrimerosTres.forEach(label => {
            label.textContent = label.textContent.replace(/^\*/, '');
        });
        inputsPrimerosTres.forEach(input => {
            input.required = false;
        });
    }
}


