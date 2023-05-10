import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Empleado } from '../models/empleado';
import { EmpleadosService } from '../service/empleados.service';
import Swal from 'sweetalert2';
import { CoreService } from 'src/app/core/core.service';


@Component({
  selector: 'app-emp-delete',
  templateUrl: './emp-delete.component.html',
  styleUrls: ['./emp-delete.component.css']
})
export class EmpDeleteComponent {
  constructor(
    
    public dialogRef: MatDialogRef<EmpDeleteComponent>,
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
  
          const swalWithBootstrapButtons = Swal.mixin({
            customClass: {
              confirmButton: 'btn btn-success',
              cancelButton: 'btn btn-danger'
            },
            buttonsStyling: false
          })
          
          swalWithBootstrapButtons.fire({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Yes, delete it!',
            cancelButtonText: 'No, cancel!',
            reverseButtons: true
          }).then((result) => {
            if (result.isConfirmed) {
              this.dataService.deleteItem(this.data.id);
     
            } else if (
              /* Read more about handling dismissals below */
              result.dismiss === Swal.DismissReason.cancel
            ) {
              swalWithBootstrapButtons.fire(
                'Cancelled',
                'Your imaginary file is safe :)',
                'error'
              )
            }
          })   
        }

}
