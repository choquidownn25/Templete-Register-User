import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import Swal from 'sweetalert2';
@Injectable({
  providedIn: 'root'
})
export class EmpleadosService {
  baseUrl = environment.API_URL_TODOS_PERSONA_JAVA;
  addUrl = environment.API_URL_TODOS_ADD_PERSONA_JAVA;
  editUrl = environment.API_URL_TODOS_EDIT_PERSONA_JAVA;
  deleteUrl = environment.API_URL_TODOS_DELETE_PERSONA_JAVA;
  baseUrlById = environment.API_URL_TODOS_FINDBYID_PERSONA_JAVA;
  dialogData: any;
  constructor(private httpClient: HttpClient) {}

  public getData = ( ) => {
    let timerInterval
Swal.fire({
  title: 'Load!',
  html: 'Data procesing .<b></b>',
  timer: 2000,
  timerProgressBar: true,
  didOpen: () => {
    Swal.showLoading()
    timerInterval = setInterval(() => {
    }, 100)
  },
  willClose: () => {
    clearInterval(timerInterval)
  }
}).then((result) => {
  /* Read more about handling dismissals below */
  if (result.dismiss === Swal.DismissReason.timer) {
    console.log('I was closed by the timer')
  }
})
    return this.httpClient.get(this.baseUrl);
  }
  addEmployee(data: any): Observable<any> {
    return this.httpClient.post(this.addUrl, data);
  }

  getByIdEmployee(id: number, data: any): Observable<any> {
    return this.httpClient.put(this.baseUrlById + id, data);
  }

  getEmployeeList(): Observable<any> {
    return this.httpClient.get(this.baseUrl);
  }

  deleteEmployee(id: number): Observable<any> {
    return this.httpClient.delete(`http://localhost:3000/employees/${id}`);
  }
  // ADD, POST METHOD
  addItem(formData :FormData): void {
    //let fileToUpload = <File>files[0];
    //const formData = new FormData();
    //formData.append('file', persona.nombre);
    this.httpClient.post(this.addUrl, formData).subscribe(() => {
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
    this.httpClient.put(this.editUrl, formData).subscribe(() => {
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
    this.httpClient.delete(this.deleteUrl + id).subscribe(() => {
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

  updateEmployee(id: number, data: any): Observable<any> {
    return this.httpClient.put(`http://localhost:3000/employees/${id}`, data);
  }



}
