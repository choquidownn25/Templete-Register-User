import { PersonaService } from '../../service/persona.service';
import { Component, Inject } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Persona } from '../../models/persona';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-editar',
  templateUrl: './editar.component.html',
  styleUrls: ['./editar.component.css']
})
export class EditarComponent {
  selectedFiles: any[];
  constructor(public dialogRef: MatDialogRef<EditarComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Persona,
    public dataService: PersonaService) { }

    formControl = new FormControl('', [
      Validators.required
      // Validators.email,
      ]);
      
      getErrorMessage() {
      return this.formControl.hasError('required') ? 'Required field' :
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
        formData.append("Id", this.data.id.toString());
        formData.append("Nombre", this.data.nombre);
        formData.append("Apellido", this.data.apellido);
        formData.append("FechaNacimiento", this.data.fechaNacimiento);
        formData.append("Foto", this.data.foto);
        formData.append("EstadoCivil", this.data.estadoCivil.toString());
        formData.append("TieneHermanod", this.data.tieneHermanod.toString());
        //this.dataService.addItem(formData);
      this.dataService.updateItem(formData);
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
