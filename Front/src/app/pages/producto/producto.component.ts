import { HttpClient } from '@angular/common/http';
import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import Swal from 'sweetalert2';

import { AddComponent } from './dialogs/add/add.component';
import { DeleteComponent } from './dialogs/delete/delete.component';
import { EditComponent } from './dialogs/edit/edit.component';
import { Producto } from './models/producto';
import { ProductoService } from './services/producto.service';



@Component({
  selector: 'app-producto',
  templateUrl: './producto.component.html',
  styleUrls: ['./producto.component.css']
})
export class ProductoComponent implements OnInit , AfterViewInit {
  displayedColumns = ['id', 'nombre', 'descripcion', 'precio', 'cantidad', 'imagen', 'actions'];
  //displayedColumns = ['id', 'nombre', 'descripcion', 'precio', 'cantidad', 'imagen'];
  public productos = new MatTableDataSource<Producto>()
  @ViewChild(MatSort)
  sort!: MatSort;
  @ViewChild(MatPaginator)
  paginator!: MatPaginator;
  index!: number;
  id!: number;

  constructor(
    public dialog: MatDialog,
    public dataService: ProductoService) {
  
  }
  ngAfterViewInit(): void {
    this.productos.sort = this.sort;
    this.productos.paginator = this.paginator;
  }
  ngOnInit(){
    
    this.getAllProducto();
  }
  
  public getAllProducto = () => {
   
    
    
    this.dataService.getData()
    .subscribe(res => {
     
      this.productos.data = res as Producto[];
    })
  }
  public doFilter = (event: Event) => {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLocaleLowerCase();
    this.productos.filter = filterValue;
  }
  addNew() {
    const dialogRef = this.dialog.open(AddComponent, {
      data: {producto: Producto }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 1) {
        // After dialog is closed we're doing frontend updates
        // For add we're just pushing a new row inside DataService
       // this.exampleDatabase.dataChange.value.push(this.dataService.getDialogData());
        //this.refreshTable();
        this.getAllProducto();
      }
      //this.getAllProducto();
    });
  }

  startEdit(i: number, id: number, nombre: string, descripcion: string, precio: number, cantidad: number, imagen: string) {
    this.id = id;
    // index row is used just for debugging proposes and can be removed
    this.index = i;
    //console.log(this.index);
    const dialogRef = this.dialog.open(EditComponent, {
      data: {id: id, nombre: nombre, descripcion: descripcion, precio: precio, cantidad: cantidad, imagen: imagen}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 1) {
        // When using an edit things are little different, firstly we find record inside DataService by id
        //const foundIndex = this.exampleDatabase.dataChange.value.findIndex(x => x.id === this.id);
        // Then you update that record using data from dialogData (values you enetered)
        //this.exampleDatabase.dataChange.value[foundIndex] = this.dataService.getDialogData();
        // And lastly refresh table
        //this.refreshTable();
        this.getAllProducto();
      }
    });
  }
 
  deleteItem(i: number, id: number, nombre: string, descripcion: string, precio: number, cantidad: number, imagen: string) {
    this.index = i;
    this.id = id;

    const swalWithBootstrapButtons = Swal.mixin({
      customClass: {
        confirmButton: 'btn btn-success',
        cancelButton: 'btn btn-danger'
      },
      buttonsStyling: false
    })

    const dialogRef = this.dialog.open(DeleteComponent, {
      data: {id: id, nombre: nombre, descripcion: descripcion, precio: precio, cantidad: cantidad, imagen: imagen}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 1) {
        //const foundIndex = this.exampleDatabase.dataChange.value.findIndex(x => x.id === this.id);
        // for delete we use splice in order to remove single object from DataService
        //this.exampleDatabase.dataChange.value.splice(foundIndex, 1);
        //this.refreshTable();
        this.getAllProducto();
      }
    });
    
  }



}
