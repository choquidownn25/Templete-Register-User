import { Component, ViewChild } from '@angular/core';
import {
  CreditCard,
  CreditCardStyle,
} from '../models copy/credit-card.interface';
import { CardLabel, FormLabel } from 'ngx-interactive-paycard';
import { compileClassMetadata } from '@angular/compiler';
import { Tab2Component } from './tabs/tab2/tab2.component';
import { Tab3Component } from './tabs/tab3/tab3.component';
import { Tab4Component } from './tabs/tab4/tab4.component';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css'],
})
export class CardComponent {
  selectedIndex = 0;

  @ViewChild('tab2Component') tab2Component!: Tab2Component;
  @ViewChild('tab3Component') tab3Component!: Tab3Component;
  @ViewChild('tab4Component') tab4Component!: Tab4Component;

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
    console.log('Evento desde PayCard:', $event);

    if (this.tab2Component?.form?.valid) {
      console.log('Datos formulario Tab 2:', this.tab2Component.form.value);
    }

    if (this.tab3Component?.form?.valid) {
      console.log('Datos formulario Tab 3:', this.tab3Component.form.value);
    }

    if (this.tab4Component?.form?.valid) {
      console.log('Datos formulario Tab 4:', this.tab4Component.form.value);
    }
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

  onTabChange(index: number) {
    this.selectedIndex = index;
  }

  onFormOpened(componentName: string) {
    console.log(`Se ha abierto el componente: ${componentName}`);
  }
}
