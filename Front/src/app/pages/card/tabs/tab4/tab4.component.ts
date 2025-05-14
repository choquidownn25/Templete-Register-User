import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-tab4',
  templateUrl: './tab4.component.html',
  styleUrls: ['./tab4.component.css'],
})
export class Tab4Component implements OnInit {
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
    console.log('Formulario cargado (Tab 4)');
    this.formOpened.emit('Formulario 4');
  }
}
