import { Component } from '@angular/core';
import {
  CreditCard,
  CreditCardStyle,
} from '../models copy/credit-card.interface';
import { CardLabel, FormLabel } from 'ngx-interactive-paycard';
import { compileClassMetadata } from '@angular/compiler';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css'],
})
export class CardComponent {
  logo: string = './assets/logo.png';
  title = 'ngx-interactive-paycard-demo';
  cardNumberFormat = '#### #### #### ####';
  cardNumberMask = '#### **** **** ####';
  //ex: Optional cardLabels - Spanish
  cardLabel: CardLabel = {
    expires: 'Expira',
    cardHolder: 'Nombre del Titular',
    fullName: 'Nombre completo',
    mm: 'MM',
    yy: 'AA',
  };
  //ex: Optional formLabels - Spanish
  formLabel: FormLabel = {
    cardNumber: 'Número de Tarjeta',
    cardHolderName: 'Titular de la Tarjeta',
    expirationDate: 'Fecha de Expiracion',
    expirationMonth: 'Mes',
    expirationYear: 'Año',
    cvv: 'CVV',
    submitButton: 'Enviar',
  };

  onSubmitEvent($event) {
    console.log('Envio de formulario');
    console.log($event);
  }

  showChangesCard($event) {
    const cardCredit: string = $event.cardNumber;
    // any changes on card (number, name, month, year, cvv)
    console.log($event);
    console.log('Tarjeta  Cambio 1' + $event);
    if (cardCredit.startsWith('4')) {
      console.log('Tarjeta de credito Visa');
      this.logo = './assets/visa.webp';
    } else if (cardCredit.startsWith('5')) {
      console.log('Tarjeta de credito MasterCard');
      this.logo = './assets/mastercard.webp';
    } else if (cardCredit.startsWith('3')) {
      console.log('Tarjeta de credito American Express');
      this.logo = './assets/american-express.webp';
    } else if (cardCredit.startsWith('6')) {
      console.log('Tarjeta de credito Discover');
    } else if (cardCredit.startsWith('7')) {
      console.log('Tarjeta de credito JCB');
    } else if (cardCredit.startsWith('8')) {
      console.log('Tarjeta de credito Diners Club');
    } else if (cardCredit.startsWith('9')) {
      console.log('Tarjeta de credito UnionPay');
    } else if (cardCredit.startsWith('0')) {
      console.log('Tarjeta de credito Maestro');
    } else {
      console.log('Tarjeta de credito Desconocida');
      this.logo = './assets/logo.png';
    }
    console.log('Número de tarjeta ' + $event.cardNumber);
  }

  showChangesCardNumber($event) {
    // any changes on card number
    if ($event.cardNumber) {
      console.log('Número de tarjeta Cambio 2' + $event.cardNumber);
    }
    console.log($event);
  }
}
