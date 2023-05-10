import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Empleado } from '../models/empleado';
import { EmpleadosService } from '../service/empleados.service';
import Swal from 'sweetalert2';
import { CoreService } from 'src/app/core/core.service';


@Component({
  selector: 'app-emp-detail',
  templateUrl: './emp-detail.component.html',
  styleUrls: ['./emp-detail.component.css']
})
export class EmpDetailComponent {
  constructor(
    
    public dialogRef: MatDialogRef<EmpDetailComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Empleado,
    private _coreService: CoreService,
    public dataService: EmpleadosService) { 

    }
    submit() {
      // empty stuff
      }
      
      onNoClick(): void {
      this.dialogRef.close();
      }
      print() {
        window.print();
      }
}
