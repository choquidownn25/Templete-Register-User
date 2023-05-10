import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { Empleado } from './models/empleado';
import { SelectionModel } from '@angular/cdk/collections';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { MatDialog } from '@angular/material/dialog';
import { EmpleadosService } from './service/empleados.service';
import Swal from 'sweetalert2';
import { EmpAddEditComponent } from './emp-add-edit/emp-add-edit.component';
import { EmpEditComponent } from './emp-edit/emp-edit.component';
import { EmpDeleteComponent } from './emp-delete/emp-delete.component';
import { EmpDetailComponent } from './emp-detail/emp-detail.component';

@Component({
  selector: 'app-empleado',
  templateUrl: './empleado.component.html',
  styleUrls: ['./empleado.component.css']
})
export class EmpleadoComponent implements OnInit , AfterViewInit  {
  selection = new SelectionModel<Empleado>(true, []);
  public displayedColumn: any;
  displayedColumns = ['id', 'nombre', 'apellido', 'fechaNacimiento', 'foto', 'estadocivil', 'tieneHermanos', 'actions'];
  //public productos = new MatTableDataSource<Empleado>()
  public personas = new MatTableDataSource<Empleado>()
  @ViewChild(MatSort)
  sort!: MatSort;
  @ViewChild(MatPaginator)
  paginator!: MatPaginator;
  index!: number;
  id!: number;
  
  constructor(
    public dialog: MatDialog,
    public dataServicePersona: EmpleadosService) {
  
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
    checkboxLabel(row?: Empleado): string {
      if (!row) {
        return `${this.isAllSelected() ? 'deselect' : 'select'} all`;
      }
      return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.id + 1}`;
    }
    public getAllPersona = () => {
      this.dataServicePersona.getData()
      .subscribe(res => {
        this.personas.data = res as Empleado[];
      })
    }
  
    public doFilter = (event: Event) => {
      const filterValue = (event.target as HTMLInputElement).value.trim().toLocaleLowerCase();
      this.personas.filter = filterValue;
    }
    addNew() {
      const dialogRef = this.dialog.open(EmpAddEditComponent, {
        data: {persona:  Empleado }
      });
  
      dialogRef.afterClosed().subscribe(result => {
        if (result === 1) {
  
          this.getAllPersona();
        }
        this.getAllPersona();
      });
    }
    //displayedColumns = ['id', 'nombre', 'apellido', 'fechaNacimiento', 'foto', 'estadoCivil', 'tieneHermanod', 'actions'];
    startDedail(i: number, id: number, nombre: string, apellido: string, fechaNacimiento: Date, foto: string, estadocivil: number, tieneHermanos: boolean) {
      this.id = id;
      // index row is used just for debugging proposes and can be removed
      this.index = i;
      //console.log(this.index);
      const dialogRef = this.dialog.open(EmpDetailComponent, {
        data: {id: id, nombre: nombre, apellido: apellido, fechaNacimiento: fechaNacimiento, foto: foto, estadocivil: estadocivil, tieneHermanos: tieneHermanos}
      });
  
      dialogRef.afterClosed().subscribe(result => {
        if (result === 1) {
            this.getAllPersona();
        }
      });
    }
    startEdit(i: number, id: number, nombre: string, apellido: string, fechaNacimiento: Date, foto: string, estadocivil: number, tieneHermanos: boolean) {
      this.id = id;
      // index row is used just for debugging proposes and can be removed
      this.index = i;
      //console.log(this.index);
      const dialogRef = this.dialog.open(EmpEditComponent, {
        data: {id: id, nombre: nombre, apellido: apellido, fechaNacimiento: fechaNacimiento, foto: foto, estadocivil: estadocivil, tieneHermanos: tieneHermanos}
      });
  
      dialogRef.afterClosed().subscribe(result => {
        if (result === 1) {
            this.getAllPersona();
        }
      });
    }
     deleteItem(i: number, id: number, nombre: string, apellido: string, fechaNacimiento: Date, foto: string, estadocivil: number, tieneHermanos: boolean) {
      this.index = i;
      this.id = id;
  
      const swalWithBootstrapButtons = Swal.mixin({
        customClass: {
          confirmButton: 'btn btn-success',
          cancelButton: 'btn btn-danger'
        },
        buttonsStyling: false
      })
  
      const dialogRef = this.dialog.open(EmpDeleteComponent, {
        data: {id: id, nombre: nombre, apellido: apellido, fechaNacimiento: fechaNacimiento, foto: foto, estadocivil: estadocivil, tieneHermanos: tieneHermanos}
      });
  
      dialogRef.afterClosed().subscribe(result => {
        if (result === 1) {
          this.getAllPersona();
        }
      });
      
    }
  ngOnInit(): void {
    this.displayedColumn = this.getDisplayedColumns();
    this.getAllPersona();
  }
  getDisplayedColumns() {
    return  ['id', 'nombre', 'apellido', 'fechaNacimiento', 'foto', 'estadocivil', 'tieneHermanos', 'actions'];
  }
}
