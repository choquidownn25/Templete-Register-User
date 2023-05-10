import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import Swal from 'sweetalert2';
import { Producto } from '../producto/models/producto';
import { ProductoService } from '../producto/services/producto.service';
import { Persona } from './models/persona';
import { PersonaService } from './service/persona.service';
import { AgregarComponent } from './dialogs/agregar/agregar.component';
import { EditarComponent } from './dialogs/editar/editar.component';
import { SelectionModel } from '@angular/cdk/collections';
import { EliminarComponent } from './dialogs/eliminar/eliminar.component';



@Component({
  selector: 'app-persona',
  templateUrl: './persona.component.html',
  styleUrls: ['./persona.component.css']
})
export class PersonaComponent implements OnInit , AfterViewInit {
  selection = new SelectionModel<Persona>(true, []);
  displayedColumn = ['id', 'nombre', 'descripcion', 'precio', 'cantidad', 'imagen', 'actions'];
  displayedColumns = ['id', 'nombre', 'apellido', 'fechaNacimiento', 'foto', 'estadoCivil', 'tieneHermanod', 'actions'];
  public productos = new MatTableDataSource<Producto>()
  public personas = new MatTableDataSource<Persona>()
  @ViewChild(MatSort)
  sort!: MatSort;
  @ViewChild(MatPaginator)
  paginator!: MatPaginator;
  index!: number;
  id!: number;

  constructor(
    public dialog: MatDialog,
    public dataService: ProductoService,
    public dataServicePersona: PersonaService) {
  
  }
  ngAfterViewInit(): void {
    this.personas.sort = this.sort;
    this.personas.paginator = this.paginator;
  }

  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.personas.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  toggleAllRows() {
    if (this.isAllSelected()) {
      this.selection.clear();
      return;
    }

    this.selection.select(...this.personas.data);
  }

    /** The label for the checkbox on the passed row */
    checkboxLabel(row?: Persona): string {
      if (!row) {
        return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
      }
      return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;
    }

  ngOnInit(){
   
    this.getAllPersona();
  }
  public getAllPersona = () => {
    this.dataServicePersona.getData()
    .subscribe(res => {
      this.personas.data = res as Persona[];
    })
  }

  public doFilter = (event: Event) => {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLocaleLowerCase();
    this.personas.filter = filterValue;
  }
  addNew() {
    const dialogRef = this.dialog.open(AgregarComponent, {
      data: {persona:  Persona }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 1) {

        this.getAllPersona();
      }
      //this.getAllProducto();
    });
  }
  //displayedColumns = ['id', 'nombre', 'apellido', 'fechaNacimiento', 'foto', 'estadoCivil', 'tieneHermanod', 'actions'];
 
  startEdit(i: number, id: number, nombre: string, apellido: string, fechaNacimiento: Date, foto: string, estadoCivil: string, tieneHermanod: boolean) {
    this.id = id;
    // index row is used just for debugging proposes and can be removed
    this.index = i;
    //console.log(this.index);
    const dialogRef = this.dialog.open(EditarComponent, {
      data: {id: id, nombre: nombre, apellido: apellido, fechaNacimiento: fechaNacimiento, foto: foto, estadoCivil: estadoCivil, tieneHermanod: tieneHermanod}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 1) {
          this.getAllPersona();
      }
    });
  }
   deleteItem(i: number, id: number, nombre: string, apellido: string, fechaNacimiento: Date, foto: string, estadoCivil: string, tieneHermanod: boolean) {
    this.index = i;
    this.id = id;

    const swalWithBootstrapButtons = Swal.mixin({
      customClass: {
        confirmButton: 'btn btn-success',
        cancelButton: 'btn btn-danger'
      },
      buttonsStyling: false
    })

    const dialogRef = this.dialog.open(EliminarComponent, {
      data: {id: id, nombre: nombre, apellido: apellido, fechaNacimiento: fechaNacimiento, foto: foto, estadoCivil: estadoCivil, tieneHermanod: tieneHermanod}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 1) {
        this.getAllPersona();
      }
    });
    
  }
}
