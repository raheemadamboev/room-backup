package xyz.teamgravity.roombackup.presentation.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import xyz.teamgravity.roombackup.data.model.KeyModel
import xyz.teamgravity.roombackup.data.repository.BackupRepository
import xyz.teamgravity.roombackup.data.repository.KeyRepository
import java.util.*
import javax.inject.Inject

@HiltViewModel
class KeyListViewModel @Inject constructor(
    private val repository: KeyRepository,
    private val backup: BackupRepository,
) : ViewModel() {

    var keys: List<KeyModel> by mutableStateOf(emptyList())
        private set

    private var observeKeysJob: Job? = null

    init {
        observe()
    }

    fun onAddKey() {
        viewModelScope.launch(NonCancellable + Dispatchers.IO) {
            repository.insertKey(
                KeyModel(
                    id = 0,
                    key = UUID.randomUUID().toString()
                )
            )
        }
    }

    fun onDeleteKey() {
        viewModelScope.launch(NonCancellable + Dispatchers.IO) {
            repository.deleteLatestKey()
        }
    }

    fun onExport(uri: Uri) {
        viewModelScope.launch {
            backup.export(uri)
            observe()
        }
    }

    private fun observe() {
        observeKeys()
    }

    private fun observeKeys() {
        observeKeysJob?.cancel()
        observeKeysJob = viewModelScope.launch {
            repository.getAllKeys().collectLatest { keys ->
                this@KeyListViewModel.keys = keys
            }
        }
    }
}