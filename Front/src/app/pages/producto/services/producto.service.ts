import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import { Producto } from '../models/producto';
import { environment } from 'src/environments/environment';
import Swal from 'sweetalert2';
import { Persona } from '../../persona/models/persona';
@Injectable()
export class ProductoService {
  baseUrlPersonas = environment.API_URL_TODOS_ADD_PERSONA;
  baseUrl = environment.API_URL_TODOS_TICKET;
  addUrl = environment.API_URL_TODOS_ADD;
  editUrl = environment.API_URL_TODOS_EDIT;
  deleteUrl = environment.API_URL_TODOS_DELETE;
  dialogData: any;
  dataChange: BehaviorSubject<Producto[]> = new BehaviorSubject<Producto[]>([]);
  environment: any;
  productoList: Producto[] = [];
  personaList: Persona[] = [];
  constructor(private httpClient: HttpClient) { }

  get data(): Producto[] {
    return this.dataChange.value;
  }

  getDialogData() {
    return this.dialogData;
  }
  public getData = ( ) => {
    return this.httpClient.get(this.baseUrl);
  }
  
  get(): Observable<Persona[]> {
    
    return this.httpClient.get<Persona[]>(this.baseUrlPersonas);
   }
  getAllProducto(): void {
    this.httpClient.get<Persona[]>(this.baseUrlPersonas).subscribe(data => {
        //this.dataChange.next(data);
        this.personaList = data;
      },
      (error: HttpErrorResponse) => {
      console.log (error.name + ' ' + error.message);
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: 'Something went wrong!' + "\n" + error.name + ' ' + error.message,
        footer: '<a href="">Why do I have this issue?</a>'
      })

      });
  }
   // ADD, POST METHOD
   addItem(producto: Producto): void {
    this.httpClient.post(this.addUrl, producto).subscribe(data => {
      this.dialogData = producto;
      Swal.fire(
        'Good job!',
        'You clicked the button!',
        'success'
      )
      },
      (err: HttpErrorResponse) => {
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Something went wrong!' + "\n" + err.message.toString,
          footer: '<a href="">Why do I have this issue?</a>'
        })
    });
   }

    // UPDATE, PUT METHOD
     updateItem(producto: Producto): void {
    this.httpClient.post(this.editUrl, producto).subscribe(data => {
        this.dialogData = producto;
        Swal.fire(
          'Good job!',
          'You clicked the button!',
          'success'
        )
      },
      (err: HttpErrorResponse) => {
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Something went wrong!' + "\n" + err.message.toString,
          footer: '<a href="">Why do I have this issue?</a>'
        })
      }
    );
  }

  // DELETE METHOD
  deleteItem(id: number): void {
    this.httpClient.delete(this.deleteUrl + id).subscribe(data => {
      //console.log(data['']);
      Swal.fire(
        'Good job!',
        'You clicked the button!',
        'success'
      )
      },
      (err: HttpErrorResponse) => {
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Something went wrong!' + "\n" + err.message.toString,
          footer: '<a href="">Why do I have this issue?</a>'
        })
      }
    );
  }
}
