package com.sepanta.controlkit.netpromoterscoreexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sepanta.controlkit.netpromoterscore.config.NetPromoterScoreServiceConfig
import com.sepanta.controlkit.netpromoterscore.netPromoterScoreKitHost
import com.sepanta.controlkit.netpromoterscore.view.config.NetPromoterScoreViewConfig
import com.sepanta.controlkit.netpromoterscore.view.config.NetPromoterScoreViewStyle
import com.sepanta.controlkit.netpromoterscoreexample.ui.theme.NetPromoterScoreExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NetPromoterScoreExampleTheme {
                Surface (modifier = Modifier.fillMaxSize()) {
                    val kit = netPromoterScoreKitHost(
                        NetPromoterScoreServiceConfig(
                            version = "1.0.0",
                            name="test2",
                            appId = "9fee1663-e80e-46ad-8cd9-357263375a9c",
                            deviceId = "dsd",
                            viewConfig = NetPromoterScoreViewConfig(
                                NetPromoterScoreViewStyle.Popover2
                            )
                        ),
                        onDismiss = {

                        }
                    )
                    kit.showView()
                }

            }
        }
    }
}

