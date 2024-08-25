package com.example.ejercicio9

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ejercicio9.ui.theme.Ejercicio9Theme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskListScreen()
        }
    }
}

data class Task(val id: Int, val title: String, var isCompleted: Boolean)

@Composable
fun TaskListScreen() {
    var tasks by remember { mutableStateOf(listOf(
        Task(1, "Enviar Lab03", true),
        Task(2, "Hacer ejercicio", false),
        Task(3, "Cenar", false),
        Task(4,"Cepillarse los dientes", false)

    )) }
    var newTaskTitle by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Lista de Tareas Por Hacer",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        //añadir tarea
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = newTaskTitle,
                onValueChange = { newTaskTitle = it },
                label = { Text("Nueva tarea") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (newTaskTitle.isNotBlank()) {
                    tasks = tasks + Task(tasks.size + 1, newTaskTitle, false)
                    newTaskTitle = ""
                }
            }) {
                Text("Añadir")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        //lista
        LazyColumn {
            items(tasks) { task ->
                TaskItem(
                    task = task,
                    onTaskComplete = { completedTask ->
                        tasks = tasks.map { if (it.id == completedTask.id) completedTask else it }
                    }
                )
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, onTaskComplete: (Task) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = {
                onTaskComplete(task.copy(isCompleted = it))
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = task.title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTaskListScreen() {
    TaskListScreen()
}