/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function valida(f) {
    var ok = true;
    var msg = "Error:\n";
    if (f.elements["ci"].value.trim() == "")
    {
        msg += " Debe introducir su número de cédula\n";
        ok = false;
    }

    // isNaN devuelve false si es un numero, 
    if (isNaN(f.elements["ci"].value)) //si devuelve true es un string
    {
        msg += " No puede introducir un texto";
        ok = false;
    }

    if (f.elements["pas"].value.trim() == "")
    {
        msg += " No puede dejar vacio este campo";
        ok = false;
    }

    if (ok == false)
        alert(msg);
    return ok;
}

function habilita(f) {
    var ok = true;
    var msg = "Error:\n";
    var elem = document.getElementsById("tarjeta");
    if (elem.value == "tarjeta")
    {
        document.getElementsById("numTarjeta").innerHTML = number;
        out.print(document.getElementsById("numTarjeta").innerHTML);
    }
    return ok;
}
