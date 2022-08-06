package xyz.teamgravity.roombackup.presentation.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import xyz.teamgravity.roombackup.R
import xyz.teamgravity.roombackup.core.constant.Const
import xyz.teamgravity.roombackup.presentation.viewmodel.KeyListViewModel

@Composable
fun KeyListScreen(
    viewmodel: KeyListViewModel = hiltViewModel(),
) {
    val exportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument(Const.MIME_SQLITE3),
        onResult = { uri ->
            if (uri != null) viewmodel.onExport(uri)
        }
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.keys))
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = viewmodel::onAddKey,
                    modifier = Modifier.weight(1F)
                ) {
                    Text(text = stringResource(id = R.string.add_key))
                }
                Spacer(modifier = Modifier.width(10.dp))
                FilledTonalButton(
                    onClick = viewmodel::onDeleteKey,
                    modifier = Modifier.weight(1F)
                ) {
                    Text(text = stringResource(id = R.string.delete_key))
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.width(10.dp))
                FilledTonalButton(
                    onClick = { exportLauncher.launch(Const.DATABASE_FILE_NAME) },
                    modifier = Modifier.weight(1F)
                ) {
                    Text(text = stringResource(id = R.string.export_db))
                }
                Spacer(modifier = Modifier.width(10.dp))
                FilledTonalButton(
                    onClick = {},
                    modifier = Modifier.weight(1F)
                ) {
                    Text(text = stringResource(id = R.string.import_db))
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn {
                items(viewmodel.keys) { key ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp, horizontal = 10.dp)
                    ) {
                        Text(
                            text = key.key,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}