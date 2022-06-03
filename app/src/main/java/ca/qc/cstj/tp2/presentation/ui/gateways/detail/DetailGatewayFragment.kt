package ca.qc.cstj.tp2.presentation.ui.gateways.detail

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import ca.qc.cstj.tp2.R
import ca.qc.cstj.tp2.core.ColorHelper
import ca.qc.cstj.tp2.core.LoadingResource
import ca.qc.cstj.tp2.core.loadFromResource
import ca.qc.cstj.tp2.databinding.FragmentDetailGatewayBinding
import ca.qc.cstj.tp2.domain.models.Gateway

class DetailGatewayFragment : Fragment(R.layout.fragment_detail_gateway){
    private val binding: FragmentDetailGatewayBinding by viewBinding()
    private val viewModel: DetailGatewayViewModel by viewModels{
        DetailGatewayViewModel.Factory(args.href)
    }

    private val args : DetailGatewayFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.gateway.observe(viewLifecycleOwner){
            when(it){
                is LoadingResource.Error -> {
                    Toast.makeText(requireContext(), it.throwable.message, Toast.LENGTH_LONG).show()
                    //pour revenir au fragment d'avant
                    Log.d("MESSAGE CACA", it.throwable.message.toString())
                    requireActivity().supportFragmentManager.popBackStack()
                }
                is LoadingResource.Success -> {
                    val gateway = it.data!!

                    showGatewayInfo(gateway)
                }
                is LoadingResource.Loading -> {

                }
            }
        }

        binding.btnReboot.setOnClickListener {
            if(viewModel.isGatewayOnline())
                viewModel.reboot()
        }

        binding.btnUpdate.setOnClickListener {
            if(viewModel.isGatewayOnline())
                viewModel.update()
        }
    }

    private fun showGatewayInfo(gateway: Gateway){

        if (gateway.connection.status == "Online"){
            with(binding){
                grpInfoGateway.visibility = View.VISIBLE
                txvOffline.visibility = View.INVISIBLE
                txvIPAddress.text = gateway.connection.ip
                txvPing.text = "${gateway.connection.ping} ns"
                txvDownload.text = "${gateway.connection.download} Ebps"
                txvUpload.text = "${gateway.connection.upload} Ebps"
                txvSignalQuality.text = "${gateway.connection.signal} dBm"

            }
        } else {
            with(binding){
                grpInfoGateway.visibility = View.INVISIBLE
                txvOffline.visibility = View.VISIBLE
            }
        }
        with(binding){
            chpStatusGateway.chipBackgroundColor = ColorHelper.connectionStatusColor(root.context, gateway.connection.status)
            chpStatusGateway.text = gateway.connection.status
            txvSerialNumber.text = gateway.serialNumber
            txvMacAddress.text = gateway.config.mac
            txvSSID.text = "SSID: ${gateway.config.SSID}"
            txvPIN.text = "PIN: ${gateway.pin}"
            txvKernelRevisionVersion.text = "Kernel Revision: ${gateway.config.kernelRevision} Version: ${gateway.config.version}"

            //les kernels
            imvKernel1.loadFromResource(requireContext(), "element_"+ gateway.config.kernel[0].lowercase())
            imvKernel2.loadFromResource(requireContext(), "element_"+ gateway.config.kernel[1].lowercase())
            imvKernel3.loadFromResource(requireContext(), "element_"+ gateway.config.kernel[2].lowercase())
            imvKernel4.loadFromResource(requireContext(), "element_"+ gateway.config.kernel[3].lowercase())
            imvKernel5.loadFromResource(requireContext(), "element_"+ gateway.config.kernel[4].lowercase())

            //hash, attention ça pique les yeux
            val hashStart: String = gateway.hash.substring(0,2)
            val hashEnd: String = gateway.hash.substring(gateway.hash.length -2)
            txvHashStart!!.text = hashStart
            txvHashEnd!!.text = hashEnd

            //hash partie vrm batard
            val hashCenter:String = gateway.hash.substring(2,gateway.hash.length-2)
            val chunks = hashCenter.chunked(6)
            vHash1!!.setBackgroundColor(Color.parseColor("#"+chunks[0]))
            vHash2!!.setBackgroundColor(Color.parseColor("#"+chunks[1]))
            vHash3!!.setBackgroundColor(Color.parseColor("#"+chunks[2]))
            vHash4!!.setBackgroundColor(Color.parseColor("#"+chunks[3]))
            vHash5!!.setBackgroundColor(Color.parseColor("#"+chunks[4]))
            vHash6!!.setBackgroundColor(Color.parseColor("#"+chunks[5]))
            vHash7!!.setBackgroundColor(Color.parseColor("#"+chunks[6]))
            vHash8!!.setBackgroundColor(Color.parseColor("#"+chunks[7]))
            vHash9!!.setBackgroundColor(Color.parseColor("#"+chunks[8]))
            vHash10!!.setBackgroundColor(Color.parseColor("#"+chunks[9]))


        }
    }
}