exports.validateId = function validarId(str) {
  // Ensure upcase and remove whitespace ang hyphens
  if (!str) return false;

  str = str.toUpperCase().replace(/\s/, "");

  let valido = false;
  const tipo = tipoId(str);
  switch (tipo) {
    case "dni":
      valido = validarDni(str);
      break;
    case "nie":
      valido = validarNie(str);
      break;
    case "cif":
      valido = validarCif(str);
      break;
  }

  return valido;
};

function tipoId(str) {
  let DNI_REGEX = /^(\d{8})([A-Z])$/;
  let CIF_REGEX = /^([ABCDEFGHJKLMNPQRSUVW])(\d{7})([0-9A-J])$/;
  let NIE_REGEX = /^[XYZ]\d{7,8}[A-Z]$/;

  if (str.match(DNI_REGEX)) {
    return "dni";
  }
  if (str.match(CIF_REGEX)) {
    return "cif";
  }
  if (str.match(NIE_REGEX)) {
    return "nie";
  }
}

function validarDni(str) {
  const dni_letters = "TRWAGMYFPDXBNJZSQVHLCKE";
  const letter = dni_letters.charAt(parseInt(str, 10) % 23);

  return letter === str.charAt(8);
}

function validarNie(str) {
  // Change the initial letter for the corresponding number and validate as DNI
  var nie_prefix = str.charAt(0);

  switch (nie_prefix) {
    case "X":
      nie_prefix = 0;
      break;
    case "Y":
      nie_prefix = 1;
      break;
    case "Z":
      nie_prefix = 2;
      break;
  }

  return validarDni(nie_prefix + str.substr(1));
}

function validarCif(str) {
  if (!str || str.length !== 9) {
    return false;
  }

  const letters = ["J", "A", "B", "C", "D", "E", "F", "G", "H", "I"];
  const digits = str.substr(1, str.length - 2);
  const letter = str.substr(0, 1);
  const control = str.substr(str.length - 1);
  let sum = 0;
  let i;
  let digit;

  if (!letter.match(/[A-Z]/)) {
    return false;
  }

  for (i = 0; i < digits.length; ++i) {
    digit = parseInt(digits[i]);

    if (isNaN(digit)) {
      return false;
    }

    if (i % 2 === 0) {
      digit *= 2;
      if (digit > 9) {
        digit = parseInt(digit / 10) + (digit % 10);
      }

      sum += digit;
    } else {
      sum += digit;
    }
  }

  sum %= 10;
  if (sum !== 0) {
    digit = 10 - sum;
  } else {
    digit = sum;
  }

  if (letter.match(/[ABEH]/)) {
    return String(digit) === control;
  }
  if (letter.match(/[NPQRSW]/)) {
    return letters[digit] === control;
  }

  return String(digit) === control || letters[digit] === control;
}

// Acepta NIEs (Extranjeros con X, Y o Z al principio)
/**
 * Valida los Dni y NIE
 *
 * @param {*} dni
 * @returns devuelve true si es correcto
 */
function validarDNI(dni) {
  var numero, let, letra;
  var expresion_regular_dni = /^[XYZ]?\d{5,8}[A-Z]$/;

  dni = dni.toUpperCase();

  if (expresion_regular_dni.test(dni) === true) {
    numero = dni.substr(0, dni.length - 1);
    numero = numero.replace("X", 0);
    numero = numero.replace("Y", 1);
    numero = numero.replace("Z", 2);
    let = dni.substr(dni.length - 1, 1);
    numero = numero % 23;
    letra = "TRWAGMYFPDXBNJZSQVHLCKET";
    letra = letra.substring(numero, numero + 1);
    if (letra != let) {
      //alert('Dni erroneo, la letra del NIF no se corresponde');
      return false;
    } else {
      //alert('Dni correcto');
      return true;
    }
  } else {
    //alert('Dni erroneo, formato no válido');
    return false;
  }
}