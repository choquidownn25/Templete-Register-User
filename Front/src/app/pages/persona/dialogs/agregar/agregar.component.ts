import { PersonaService } from '../../service/persona.service';
import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Persona } from '../../models/persona';
import { HttpRequest } from '@angular/common/http';
import Swal from 'sweetalert2';



@Component({
  selector: 'app-agregar',
  templateUrl: './agregar.component.html',
  styleUrls: ['./agregar.component.css']
})
export class AgregarComponent {
selectedFiles: any[];
  
constructor(public dialogRef: MatDialogRef<AgregarComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Persona,
    public dataService: PersonaService) { }

formControl = new FormControl('', [
Validators.required
// Validators.email,
]);

getErrorMessage() {
return this.formControl.hasError('required') ? 'Required field' :
this.formControl.hasError('email') ? 'Not a valid email' :
'';
}

submit() {
// empty stuff
}

onNoClick(): void {
this.dialogRef.close();
}

public confirmAdd(): void {
  if (!this.selectedFiles || this.selectedFiles.length === 0) {
    Swal.fire({
      icon: 'error',
      title: 'Oops...',
      text: 'Insertar una foto!' + "\n" ,
      footer: '<a href="">La app necesita una imgen?</a>'
    })
    return;
  }

  
  const formData = new FormData();
  this.selectedFiles.forEach((f) => formData.append('file', f));
  formData.append("Nombre", this.data.nombre);
  formData.append("Apellido", this.data.apellido);
  formData.append("FechaNacimiento", this.data.fechaNacimiento);
  formData.append("Foto", this.data.foto);
  formData.append("EstadoCivil", this.data.estadoCivil.toString());
  formData.append("TieneHermanod", this.data.tieneHermanod.toString());

    this.dataService.addItem(formData);
}


  
chooseFile(files: FileList) {
  this.selectedFiles = [];
  if (files.length === 0) {
    return;
  }
  for (let i = 0; i < files.length; i++) {
    this.selectedFiles.push(files[i]);
  }
}



}
