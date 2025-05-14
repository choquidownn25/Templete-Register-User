import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-tab2',
  templateUrl: './tab2.component.html',
  styleUrls: ['./tab2.component.css'],
})
export class Tab2Component implements OnInit {
  form: FormGroup;
  @Output() formOpened = new EventEmitter<string>();

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      campo1: [''],
      campo2: [''],
      campo3: [''],
      campo4: [''],
    });
  }
  ngOnInit(): void {
    console.log('Formulario cargado (Tab 2)');
    this.formOpened.emit('Formulario 2 cargado');
  }
}
