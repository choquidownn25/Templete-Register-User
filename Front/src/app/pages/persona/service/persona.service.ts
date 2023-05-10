import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';

import { environment } from 'src/environments/environment';
import Swal from 'sweetalert2';
import { Producto } from '../../producto/models/producto';
import { Persona } from '../models/persona';

@Injectable({
  providedIn: 'root'
})
export class PersonaService {

  baseURLPersona = environment.API_URL_TODOS_PERSONA;
  baseUrl = environment.API_URL_TODOS_TICKET;
  addUrl = environment.API_URL_TODOS_ADD;
  addUrlPersona = environment.API_URL_TODOS_ADD_PERSONA;
  editUrl = environment.API_URL_TODOS_EDIT;
  editarUrlPersona = environment.API_URL_TODOS_EDIT_PERSONA;
  deleteUrl = environment.API_URL_TODOS_DELETE;
  deleteUrlPersona = environment.API_URL_TODOS_DELETE_PERSONA;
  dialogData: any;
  dataChange: BehaviorSubject<Producto[]> = new BehaviorSubject<Producto[]>([]);
  environment: any;
  productoList: Producto[] = [];
  constructor(private httpClient: HttpClient) { }

  get data(): Producto[] {
    return this.dataChange.value;
  }

  getDialogData() {
    return this.dialogData;
  }
  public getData = ( ) => {
    return this.httpClient.get(this.baseURLPersona);
  }
 
 
  
   // ADD, POST METHOD
   addItem(formData :FormData): void {
    //let fileToUpload = <File>files[0];
    //const formData = new FormData();
    //formData.append('file', persona.nombre);
    this.httpClient.post(this.addUrlPersona, formData).subscribe(data => {
      this.dialogData = formData;
      Swal.fire(
        'Good job!',
        'Su dato quedo almacenado !',
        'success'
      )
      },
      (err: HttpErrorResponse) => {
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'algun error!' + "\n" + err.message.toString,
          footer: '<a href="">Por favor verificar?</a>'
        })
    });
   }

    // UPDATE, PUT METHOD
     updateItem(formData :FormData): void {
    this.httpClient.put(this.editarUrlPersona, formData).subscribe(data => {
        this.dialogData = formData;
        Swal.fire(
          'Good job!',
          'Su dato quedo almacenado !',
          'success'
        )
      },
      (err: HttpErrorResponse) => {
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'algun error!' + "\n" + err.message.toString,
          footer: '<a href="">Por favor verificar?</a>'
        })
      }
    );
  }

  // DELETE METHOD
  deleteItem(id: number): void {
    this.httpClient.delete(this.deleteUrlPersona + id).subscribe(data => {
      //console.log(data['']);
      Swal.fire(
        'Good job!',
        'Su dato quedo Eliminado!',
        'success'
      )
      },
      (err: HttpErrorResponse) => {
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'algun error!' + "\n" + err.message.toString,
          footer: '<a href="">Por favor verificar?</a>'
        })
      }
    );
  }
}
