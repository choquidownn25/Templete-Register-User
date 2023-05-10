import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Empleado } from '../models/empleado';
import { EmpleadosService } from '../service/empleados.service';
import Swal from 'sweetalert2';
import { CoreService } from 'src/app/core/core.service';
@Component({
  selector: 'app-emp-edit',
  templateUrl: './emp-edit.component.html',
  styleUrls: ['./emp-edit.component.css']
})
export class EmpEditComponent {
  selectedFiles: any[];

  constructor(
    
    public dialogRef: MatDialogRef<EmpEditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Empleado,
    private _coreService: CoreService,
    public dataService: EmpleadosService) { 

    }
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
        formData.append("id", this.data.id.toString());
        formData.append("Nombre", this.data.nombre);
        formData.append("Apellido", this.data.apellido);
        formData.append("FechaNacimiento", this.data.fechaNacimiento);
        formData.append("Foto", this.data.foto);
        formData.append("estadocivil", this.data.estadocivil.toString());
       
        if( this.data.tieneHermanos == undefined){
          this.data.tieneHermanos = false;
        }
        
        formData.append("tieneHermanos", this.data.tieneHermanos.toString());
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
